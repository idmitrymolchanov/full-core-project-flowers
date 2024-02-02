package org.flowers.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowers.project.api.v1.FlowerFilterBadRequestException;
import org.flowers.project.dto.v1.*;
import org.flowers.project.entity.FlowerEntity;
import org.flowers.project.entity.QFlowerEntity;
import org.flowers.project.mapper.FlowersMapper;
import org.springframework.stereotype.Service;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowersFilterService {

    @PersistenceContext
    private EntityManager entityManager;
    
    private final FlowersMapper mapper;

    public FilterResponse getFilterResponse(FilterRequest request) {
        checkFilterRequest(request);
        var queryBase = initializeQuery();
        var limit = request.getLimit();
        var offset = request.getOffset();

        if (!isFilterRequestEmpty(request)) {
            queryBase = fillQueryByRequestRules(request, queryBase);
        }

        isSortEmpty(request);
        addSorting(queryBase, request.getSort());

        var flowers = queryBase
                .limit(limit)
                .offset(offset)
                .fetch().stream().map(mapper::toFlower)
                .collect(Collectors.toList());

        return createFilterResponse(flowers, limit, offset);
    }


    private JPAQuery<FlowerEntity> initializeQuery() {
        var flower = QFlowerEntity.flowerEntity;
        var query = new JPAQuery<FlowerEntity>(entityManager);
        //скелет селекта, на который навешиваем условия и джойны
        return query.from(flower);
    }

    private void checkFilterRequest(FilterRequest request) {
        if (request.getLimit() == null || request.getLimit() <= 0)
            throw new FlowerFilterBadRequestException("Limit must be positive value");
        if (request.getOffset() == null || request.getOffset() < 0)
            throw new FlowerFilterBadRequestException("Offset must be positive value");
    }

    private boolean isFilterRequestEmpty(FilterRequest request) {
        return request == null || (isFilterEmpty(request.getFilter()));
    }

    private boolean isFilterEmpty(FlowersFilter filter) {
        return filter == null
                || (StringUtils.isEmpty(filter.getShortName())
                && StringUtils.isEmpty(filter.getFullName())
                && StringUtils.isEmpty(filter.getDescription())
                && isListEmpty(filter.getColor())
                && isListEmpty(filter.getType())
                && isCreateDateEmpty(filter.getCreateDate())
                && isCreateDateEmpty(filter.getLastUpdateDate())
        );
    }

    private boolean isCreateDateEmpty(CreateDate createDate) {
        return createDate == null
                || (createDate.getPeriodStart() == null
                && createDate.getPeriodEnd() == null
        );
    }

    private boolean isListEmpty(List<String> list) {
        return list == null || list.isEmpty();
    }

    public JPAQuery<FlowerEntity> fillQueryByRequestRules(FilterRequest request, JPAQuery<FlowerEntity> queryBase) {
        //собираем все условия where для FlowerEntity
        var predicateList = getCommonPredicates(request.getFilter());

        //преобразуем собранный список предикатов в builder
        if (!predicateList.isEmpty()) {
            var builder = getBuilderFromPredicates(predicateList);
            queryBase = queryBase.where(builder);
        }

        return queryBase;
    }

    private BooleanBuilder getBuilderFromPredicates(List<Predicate> predicateList) {
        BooleanBuilder builder = new BooleanBuilder();
        predicateList.forEach(builder::and);
        return builder;
    }

    private ArrayList<Predicate> getCommonPredicates(FlowersFilter flowersFilter) {
        var predicateList = new ArrayList<Predicate>();
        var flower = QFlowerEntity.flowerEntity;

        setStringPredicate(flowersFilter.getShortName(), predicateList, flower.shortName);
        setStringPredicate(flowersFilter.getFullName(), predicateList, flower.fullName);
        setStringPredicate(flowersFilter.getDescription(), predicateList, flower.description);

        setListPredicate(flowersFilter.getColor(), predicateList, flower.color);
        setListPredicate(flowersFilter.getType(), predicateList, flower.type);

        setTimestampPredicate(flowersFilter.getCreateDate(), predicateList, flower.createDate);
        setTimestampPredicate(flowersFilter.getLastUpdateDate(), predicateList, flower.lastUpdateDate);

        return predicateList;
    }

    private void setStringPredicate(String value, List<Predicate> predicateList, StringPath path) {
        Optional.ofNullable(value).filter(v -> !v.isEmpty()).ifPresent(v -> predicateList.add(path.eq(v)));
    }

    private void setTimestampPredicate(CreateDate createDate, List<Predicate> predicateList, DateTimePath<LocalDateTime> path) {
        if (createDate != null) {
            var periodStart = createDate.getPeriodStart();
            var periodEnd = createDate.getPeriodEnd();

            if(periodStart != null && periodEnd != null && periodStart.isAfter(periodEnd))
                throw new FlowerFilterBadRequestException("createDateTo must be is after createDateFrom");
            if (periodStart != null)
                predicateList.add(path.goe(periodStart.atStartOfDay()));
            if (periodEnd != null)
                predicateList.add(path.loe(periodEnd.plusDays(1).atStartOfDay()));
        }
    }

    private void setListPredicate(List<String> value, List<Predicate> predicateList, StringPath path) {
        Optional.ofNullable(value).filter(v -> !v.isEmpty()).ifPresent(v -> predicateList.add(path.in(v)));
    }

    private void isSortEmpty(FilterRequest request) {
        FlowersSort sort = request.getSort();
        if(sort == null || sort.getOrder() == null || sort.getFieldName() == null) {
            if (sort == null)
                sort = new FlowersSort();
            if (sort.getFieldName() == null)
                sort.setFieldName(Enums.SortingFieldEnum.CREATE_DATE);
            if (sort.getOrder() == null)
                sort.setOrder(Enums.SortingTypeEnum.DESC);
            request.setSort(sort);
        }
    }

    private void addSorting(JPAQuery<FlowerEntity> queryBase, FlowersSort flowersSort) {
        var flower = QFlowerEntity.flowerEntity;
        Expression path = null;
        var fieldName = flowersSort.getFieldName();

        if (fieldName != null) {
            switch (fieldName) {
                case COLOR:
                path = flower.color;
                break;
                case TYPE:
                path = flower.type;
                break;
                case SHORT_NAME:
                path = flower.shortName;
                break;
                case CREATE_DATE:
                path = flower.createDate;
            }
        } else
            throw new FlowerFilterBadRequestException("Field name for sorting is incorrect");

        var orderSpecifier = new OrderSpecifier(getOrderDirectionEnum(flowersSort.getOrder()), path);
        queryBase.orderBy(orderSpecifier);
    }

    private com.querydsl.core.types.Order getOrderDirectionEnum(Enums.SortingTypeEnum sortingEnum) {
        return sortingEnum == Enums.SortingTypeEnum.ASC? Order.ASC: Order.DESC;
    }

    private FilterResponse createFilterResponse(List<Flower> flowerList, Integer limit, Integer offset) {
        FilterResponse response = new FilterResponse();
        response.setFlowerList(flowerList);
        response.setLimit(limit);
        response.setOffset(offset);
        response.setTotalCount(flowerList.size());
        return response;
    }

}