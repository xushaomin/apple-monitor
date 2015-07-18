package com.appleframework.jmx.database.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppClusterEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppClusterEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andClusterNameIsNull() {
            addCriterion("cluster_name is null");
            return (Criteria) this;
        }

        public Criteria andClusterNameIsNotNull() {
            addCriterion("cluster_name is not null");
            return (Criteria) this;
        }

        public Criteria andClusterNameEqualTo(String value) {
            addCriterion("cluster_name =", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotEqualTo(String value) {
            addCriterion("cluster_name <>", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameGreaterThan(String value) {
            addCriterion("cluster_name >", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_name >=", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLessThan(String value) {
            addCriterion("cluster_name <", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLessThanOrEqualTo(String value) {
            addCriterion("cluster_name <=", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLike(String value) {
            addCriterion("cluster_name like", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotLike(String value) {
            addCriterion("cluster_name not like", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameIn(List<String> values) {
            addCriterion("cluster_name in", values, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotIn(List<String> values) {
            addCriterion("cluster_name not in", values, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameBetween(String value1, String value2) {
            addCriterion("cluster_name between", value1, value2, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotBetween(String value1, String value2) {
            addCriterion("cluster_name not between", value1, value2, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterDescIsNull() {
            addCriterion("cluster_desc is null");
            return (Criteria) this;
        }

        public Criteria andClusterDescIsNotNull() {
            addCriterion("cluster_desc is not null");
            return (Criteria) this;
        }

        public Criteria andClusterDescEqualTo(String value) {
            addCriterion("cluster_desc =", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescNotEqualTo(String value) {
            addCriterion("cluster_desc <>", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescGreaterThan(String value) {
            addCriterion("cluster_desc >", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_desc >=", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescLessThan(String value) {
            addCriterion("cluster_desc <", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescLessThanOrEqualTo(String value) {
            addCriterion("cluster_desc <=", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescLike(String value) {
            addCriterion("cluster_desc like", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescNotLike(String value) {
            addCriterion("cluster_desc not like", value, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescIn(List<String> values) {
            addCriterion("cluster_desc in", values, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescNotIn(List<String> values) {
            addCriterion("cluster_desc not in", values, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescBetween(String value1, String value2) {
            addCriterion("cluster_desc between", value1, value2, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andClusterDescNotBetween(String value1, String value2) {
            addCriterion("cluster_desc not between", value1, value2, "clusterDesc");
            return (Criteria) this;
        }

        public Criteria andIsClusterIsNull() {
            addCriterion("is_cluster is null");
            return (Criteria) this;
        }

        public Criteria andIsClusterIsNotNull() {
            addCriterion("is_cluster is not null");
            return (Criteria) this;
        }

        public Criteria andIsClusterEqualTo(Short value) {
            addCriterion("is_cluster =", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterNotEqualTo(Short value) {
            addCriterion("is_cluster <>", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterGreaterThan(Short value) {
            addCriterion("is_cluster >", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterGreaterThanOrEqualTo(Short value) {
            addCriterion("is_cluster >=", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterLessThan(Short value) {
            addCriterion("is_cluster <", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterLessThanOrEqualTo(Short value) {
            addCriterion("is_cluster <=", value, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterIn(List<Short> values) {
            addCriterion("is_cluster in", values, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterNotIn(List<Short> values) {
            addCriterion("is_cluster not in", values, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterBetween(Short value1, Short value2) {
            addCriterion("is_cluster between", value1, value2, "isCluster");
            return (Criteria) this;
        }

        public Criteria andIsClusterNotBetween(Short value1, Short value2) {
            addCriterion("is_cluster not between", value1, value2, "isCluster");
            return (Criteria) this;
        }

        public Criteria andAppNumIsNull() {
            addCriterion("app_num is null");
            return (Criteria) this;
        }

        public Criteria andAppNumIsNotNull() {
            addCriterion("app_num is not null");
            return (Criteria) this;
        }

        public Criteria andAppNumEqualTo(Integer value) {
            addCriterion("app_num =", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumNotEqualTo(Integer value) {
            addCriterion("app_num <>", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumGreaterThan(Integer value) {
            addCriterion("app_num >", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_num >=", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumLessThan(Integer value) {
            addCriterion("app_num <", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumLessThanOrEqualTo(Integer value) {
            addCriterion("app_num <=", value, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumIn(List<Integer> values) {
            addCriterion("app_num in", values, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumNotIn(List<Integer> values) {
            addCriterion("app_num not in", values, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumBetween(Integer value1, Integer value2) {
            addCriterion("app_num between", value1, value2, "appNum");
            return (Criteria) this;
        }

        public Criteria andAppNumNotBetween(Integer value1, Integer value2) {
            addCriterion("app_num not between", value1, value2, "appNum");
            return (Criteria) this;
        }

        public Criteria andDisorderIsNull() {
            addCriterion("disorder is null");
            return (Criteria) this;
        }

        public Criteria andDisorderIsNotNull() {
            addCriterion("disorder is not null");
            return (Criteria) this;
        }

        public Criteria andDisorderEqualTo(Integer value) {
            addCriterion("disorder =", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderNotEqualTo(Integer value) {
            addCriterion("disorder <>", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderGreaterThan(Integer value) {
            addCriterion("disorder >", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("disorder >=", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderLessThan(Integer value) {
            addCriterion("disorder <", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderLessThanOrEqualTo(Integer value) {
            addCriterion("disorder <=", value, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderIn(List<Integer> values) {
            addCriterion("disorder in", values, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderNotIn(List<Integer> values) {
            addCriterion("disorder not in", values, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderBetween(Integer value1, Integer value2) {
            addCriterion("disorder between", value1, value2, "disorder");
            return (Criteria) this;
        }

        public Criteria andDisorderNotBetween(Integer value1, Integer value2) {
            addCriterion("disorder not between", value1, value2, "disorder");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Short value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Short value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Short value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Short value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Short value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Short value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Short> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Short> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Short value1, Short value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Short value1, Short value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}