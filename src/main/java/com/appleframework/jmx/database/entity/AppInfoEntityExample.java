package com.appleframework.jmx.database.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppInfoEntityExample {
	
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppInfoEntityExample() {
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

        public Criteria andNodeIdIsNull() {
            addCriterion("node_id is null");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNotNull() {
            addCriterion("node_id is not null");
            return (Criteria) this;
        }

        public Criteria andNodeIdEqualTo(Integer value) {
            addCriterion("node_id =", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotEqualTo(Integer value) {
            addCriterion("node_id <>", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThan(Integer value) {
            addCriterion("node_id >", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("node_id >=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThan(Integer value) {
            addCriterion("node_id <", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThanOrEqualTo(Integer value) {
            addCriterion("node_id <=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdIn(List<Integer> values) {
            addCriterion("node_id in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotIn(List<Integer> values) {
            addCriterion("node_id not in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdBetween(Integer value1, Integer value2) {
            addCriterion("node_id between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("node_id not between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andClusterIdIsNull() {
            addCriterion("cluster_id is null");
            return (Criteria) this;
        }

        public Criteria andClusterIdIsNotNull() {
            addCriterion("cluster_id is not null");
            return (Criteria) this;
        }

        public Criteria andClusterIdEqualTo(Integer value) {
            addCriterion("cluster_id =", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotEqualTo(Integer value) {
            addCriterion("cluster_id <>", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdGreaterThan(Integer value) {
            addCriterion("cluster_id >", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cluster_id >=", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdLessThan(Integer value) {
            addCriterion("cluster_id <", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdLessThanOrEqualTo(Integer value) {
            addCriterion("cluster_id <=", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdIn(List<Integer> values) {
            addCriterion("cluster_id in", values, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotIn(List<Integer> values) {
            addCriterion("cluster_id not in", values, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdBetween(Integer value1, Integer value2) {
            addCriterion("cluster_id between", value1, value2, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cluster_id not between", value1, value2, "clusterId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppVersionIsNull() {
            addCriterion("app_version is null");
            return (Criteria) this;
        }

        public Criteria andAppVersionIsNotNull() {
            addCriterion("app_version is not null");
            return (Criteria) this;
        }

        public Criteria andAppVersionEqualTo(String value) {
            addCriterion("app_version =", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionNotEqualTo(String value) {
            addCriterion("app_version <>", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionGreaterThan(String value) {
            addCriterion("app_version >", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionGreaterThanOrEqualTo(String value) {
            addCriterion("app_version >=", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionLessThan(String value) {
            addCriterion("app_version <", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionLessThanOrEqualTo(String value) {
            addCriterion("app_version <=", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionLike(String value) {
            addCriterion("app_version like", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionNotLike(String value) {
            addCriterion("app_version not like", value, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionIn(List<String> values) {
            addCriterion("app_version in", values, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionNotIn(List<String> values) {
            addCriterion("app_version not in", values, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionBetween(String value1, String value2) {
            addCriterion("app_version between", value1, value2, "appVersion");
            return (Criteria) this;
        }

        public Criteria andAppVersionNotBetween(String value1, String value2) {
            addCriterion("app_version not between", value1, value2, "appVersion");
            return (Criteria) this;
        }

        public Criteria andWebPortIsNull() {
            addCriterion("web_port is null");
            return (Criteria) this;
        }

        public Criteria andWebPortIsNotNull() {
            addCriterion("web_port is not null");
            return (Criteria) this;
        }

        public Criteria andWebPortEqualTo(Integer value) {
            addCriterion("web_port =", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortNotEqualTo(Integer value) {
            addCriterion("web_port <>", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortGreaterThan(Integer value) {
            addCriterion("web_port >", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("web_port >=", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortLessThan(Integer value) {
            addCriterion("web_port <", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortLessThanOrEqualTo(Integer value) {
            addCriterion("web_port <=", value, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortIn(List<Integer> values) {
            addCriterion("web_port in", values, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortNotIn(List<Integer> values) {
            addCriterion("web_port not in", values, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortBetween(Integer value1, Integer value2) {
            addCriterion("web_port between", value1, value2, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebPortNotBetween(Integer value1, Integer value2) {
            addCriterion("web_port not between", value1, value2, "webPort");
            return (Criteria) this;
        }

        public Criteria andWebContextIsNull() {
            addCriterion("web_context is null");
            return (Criteria) this;
        }

        public Criteria andWebContextIsNotNull() {
            addCriterion("web_context is not null");
            return (Criteria) this;
        }

        public Criteria andWebContextEqualTo(String value) {
            addCriterion("web_context =", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextNotEqualTo(String value) {
            addCriterion("web_context <>", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextGreaterThan(String value) {
            addCriterion("web_context >", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextGreaterThanOrEqualTo(String value) {
            addCriterion("web_context >=", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextLessThan(String value) {
            addCriterion("web_context <", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextLessThanOrEqualTo(String value) {
            addCriterion("web_context <=", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextLike(String value) {
            addCriterion("web_context like", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextNotLike(String value) {
            addCriterion("web_context not like", value, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextIn(List<String> values) {
            addCriterion("web_context in", values, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextNotIn(List<String> values) {
            addCriterion("web_context not in", values, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextBetween(String value1, String value2) {
            addCriterion("web_context between", value1, value2, "webContext");
            return (Criteria) this;
        }

        public Criteria andWebContextNotBetween(String value1, String value2) {
            addCriterion("web_context not between", value1, value2, "webContext");
            return (Criteria) this;
        }

        public Criteria andJmxPortIsNull() {
            addCriterion("jmx_port is null");
            return (Criteria) this;
        }

        public Criteria andJmxPortIsNotNull() {
            addCriterion("jmx_port is not null");
            return (Criteria) this;
        }

        public Criteria andJmxPortEqualTo(Integer value) {
            addCriterion("jmx_port =", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortNotEqualTo(Integer value) {
            addCriterion("jmx_port <>", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortGreaterThan(Integer value) {
            addCriterion("jmx_port >", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("jmx_port >=", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortLessThan(Integer value) {
            addCriterion("jmx_port <", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortLessThanOrEqualTo(Integer value) {
            addCriterion("jmx_port <=", value, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortIn(List<Integer> values) {
            addCriterion("jmx_port in", values, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortNotIn(List<Integer> values) {
            addCriterion("jmx_port not in", values, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortBetween(Integer value1, Integer value2) {
            addCriterion("jmx_port between", value1, value2, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andJmxPortNotBetween(Integer value1, Integer value2) {
            addCriterion("jmx_port not between", value1, value2, "jmxPort");
            return (Criteria) this;
        }

        public Criteria andServicePortIsNull() {
            addCriterion("service_port is null");
            return (Criteria) this;
        }

        public Criteria andServicePortIsNotNull() {
            addCriterion("service_port is not null");
            return (Criteria) this;
        }

        public Criteria andServicePortEqualTo(Integer value) {
            addCriterion("service_port =", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotEqualTo(Integer value) {
            addCriterion("service_port <>", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortGreaterThan(Integer value) {
            addCriterion("service_port >", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_port >=", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortLessThan(Integer value) {
            addCriterion("service_port <", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortLessThanOrEqualTo(Integer value) {
            addCriterion("service_port <=", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortIn(List<Integer> values) {
            addCriterion("service_port in", values, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotIn(List<Integer> values) {
            addCriterion("service_port not in", values, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortBetween(Integer value1, Integer value2) {
            addCriterion("service_port between", value1, value2, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotBetween(Integer value1, Integer value2) {
            addCriterion("service_port not between", value1, value2, "servicePort");
            return (Criteria) this;
        }

        public Criteria andInstallPathIsNull() {
            addCriterion("install_path is null");
            return (Criteria) this;
        }

        public Criteria andInstallPathIsNotNull() {
            addCriterion("install_path is not null");
            return (Criteria) this;
        }

        public Criteria andInstallPathEqualTo(String value) {
            addCriterion("install_path =", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathNotEqualTo(String value) {
            addCriterion("install_path <>", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathGreaterThan(String value) {
            addCriterion("install_path >", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathGreaterThanOrEqualTo(String value) {
            addCriterion("install_path >=", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathLessThan(String value) {
            addCriterion("install_path <", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathLessThanOrEqualTo(String value) {
            addCriterion("install_path <=", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathLike(String value) {
            addCriterion("install_path like", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathNotLike(String value) {
            addCriterion("install_path not like", value, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathIn(List<String> values) {
            addCriterion("install_path in", values, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathNotIn(List<String> values) {
            addCriterion("install_path not in", values, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathBetween(String value1, String value2) {
            addCriterion("install_path between", value1, value2, "installPath");
            return (Criteria) this;
        }

        public Criteria andInstallPathNotBetween(String value1, String value2) {
            addCriterion("install_path not between", value1, value2, "installPath");
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

        public Criteria andConfDataidIsNull() {
            addCriterion("conf_dataid is null");
            return (Criteria) this;
        }

        public Criteria andConfDataidIsNotNull() {
            addCriterion("conf_dataid is not null");
            return (Criteria) this;
        }

        public Criteria andConfDataidEqualTo(String value) {
            addCriterion("conf_dataid =", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidNotEqualTo(String value) {
            addCriterion("conf_dataid <>", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidGreaterThan(String value) {
            addCriterion("conf_dataid >", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidGreaterThanOrEqualTo(String value) {
            addCriterion("conf_dataid >=", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidLessThan(String value) {
            addCriterion("conf_dataid <", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidLessThanOrEqualTo(String value) {
            addCriterion("conf_dataid <=", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidLike(String value) {
            addCriterion("conf_dataid like", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidNotLike(String value) {
            addCriterion("conf_dataid not like", value, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidIn(List<String> values) {
            addCriterion("conf_dataid in", values, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidNotIn(List<String> values) {
            addCriterion("conf_dataid not in", values, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidBetween(String value1, String value2) {
            addCriterion("conf_dataid between", value1, value2, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfDataidNotBetween(String value1, String value2) {
            addCriterion("conf_dataid not between", value1, value2, "confDataid");
            return (Criteria) this;
        }

        public Criteria andConfGroupIsNull() {
            addCriterion("conf_group is null");
            return (Criteria) this;
        }

        public Criteria andConfGroupIsNotNull() {
            addCriterion("conf_group is not null");
            return (Criteria) this;
        }

        public Criteria andConfGroupEqualTo(String value) {
            addCriterion("conf_group =", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupNotEqualTo(String value) {
            addCriterion("conf_group <>", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupGreaterThan(String value) {
            addCriterion("conf_group >", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupGreaterThanOrEqualTo(String value) {
            addCriterion("conf_group >=", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupLessThan(String value) {
            addCriterion("conf_group <", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupLessThanOrEqualTo(String value) {
            addCriterion("conf_group <=", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupLike(String value) {
            addCriterion("conf_group like", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupNotLike(String value) {
            addCriterion("conf_group not like", value, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupIn(List<String> values) {
            addCriterion("conf_group in", values, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupNotIn(List<String> values) {
            addCriterion("conf_group not in", values, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupBetween(String value1, String value2) {
            addCriterion("conf_group between", value1, value2, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfGroupNotBetween(String value1, String value2) {
            addCriterion("conf_group not between", value1, value2, "confGroup");
            return (Criteria) this;
        }

        public Criteria andConfEnvIsNull() {
            addCriterion("conf_env is null");
            return (Criteria) this;
        }

        public Criteria andConfEnvIsNotNull() {
            addCriterion("conf_env is not null");
            return (Criteria) this;
        }

        public Criteria andConfEnvEqualTo(String value) {
            addCriterion("conf_env =", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvNotEqualTo(String value) {
            addCriterion("conf_env <>", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvGreaterThan(String value) {
            addCriterion("conf_env >", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvGreaterThanOrEqualTo(String value) {
            addCriterion("conf_env >=", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvLessThan(String value) {
            addCriterion("conf_env <", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvLessThanOrEqualTo(String value) {
            addCriterion("conf_env <=", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvLike(String value) {
            addCriterion("conf_env like", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvNotLike(String value) {
            addCriterion("conf_env not like", value, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvIn(List<String> values) {
            addCriterion("conf_env in", values, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvNotIn(List<String> values) {
            addCriterion("conf_env not in", values, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvBetween(String value1, String value2) {
            addCriterion("conf_env between", value1, value2, "confEnv");
            return (Criteria) this;
        }

        public Criteria andConfEnvNotBetween(String value1, String value2) {
            addCriterion("conf_env not between", value1, value2, "confEnv");
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