package com.zkp.httpprotocol.bean;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HouseParamsBuilder {
    /**
     * 审核状态,APP个人中心需要传该字段
     */
    public interface AuditStatus {
        //待审核
        String PENDING = "pending";
        // 审核通过
        String PASS = "pass";
        // 审核不通过
        String FAILURE = "failure";
        //未发布
        String DRAFT = "draft";
    }

    /**
     * {@linkplain AuditStatus 审核状态，APP个人中心需要传该字段}
     */
    private String auditStatus;
    /**
     * 区域-市
     */
    private String city;
    /**
     * 页码
     */
    private int current = -1;
    /**
     * 一页中的数据条目数量
     */
    private int size = -1;
    /**
     * 起始价
     */
    private long priceStart = -1;
    /**
     * 最高价
     */
    private long priceEnd = -1;
    /**
     * 排序
     */
    private List<OrdersParamsBean> ordersParamsList;
    /**
     * 筛选户型
     */
    private List<Integer> roomList;
    /**
     * 筛选特色
     */
    private List<Integer> labelList;

    private Map<String, Object> extraMap;

    public String getAuditStatus() {
        return auditStatus;
    }

    public HouseParamsBuilder setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
        return this;
    }

    public String getCity() {
        return city;
    }

    public HouseParamsBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public int getCurrent() {
        return current;
    }

    public HouseParamsBuilder setCurrent(int current) {
        this.current = current;
        return this;
    }

    public int getSize() {
        return size;
    }

    public HouseParamsBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public long getPriceStart() {
        return priceStart;
    }

    public HouseParamsBuilder setPriceStart(long priceStart) {
        this.priceStart = priceStart;
        return this;
    }

    public long getPriceEnd() {
        return priceEnd;
    }

    public HouseParamsBuilder setPriceEnd(long priceEnd) {
        this.priceEnd = priceEnd;
        return this;
    }

    public List<OrdersParamsBean> getOrdersParamsList() {
        return ordersParamsList;
    }

    public HouseParamsBuilder setOrdersParamsList(List<OrdersParamsBean> ordersParamsList) {
        this.ordersParamsList = ordersParamsList;
        return this;
    }

    public List<Integer> getRoomList() {
        return roomList;
    }

    public HouseParamsBuilder setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
        return this;
    }

    public List<Integer> getLabelList() {
        return labelList;
    }

    public HouseParamsBuilder setLabelList(List<Integer> labelList) {
        this.labelList = labelList;
        return this;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public HouseParamsBuilder setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
        return this;
    }

    public Map<String, Object> build() {
        HashMap<String, Object> parmasMap = new HashMap<>();

        if (!TextUtils.isEmpty(auditStatus)) {
            parmasMap.put("auditStatus", auditStatus);
        }
        if (!TextUtils.isEmpty(city)) {
            parmasMap.put("city", city);
        }
        if (current > 0) {
            parmasMap.put("current", current);
        }
        if (size > 0) {
            parmasMap.put("size", size);
        }
        if (priceStart != -1) {
            parmasMap.put("priceStart", priceStart);
        }
        if (priceEnd != -1) {
            parmasMap.put("priceEnd", priceEnd);
        }
        if (ordersParamsList != null && !ordersParamsList.isEmpty()) {
            for (int i = 0; i < ordersParamsList.size(); i++) {
                OrdersParamsBean ordersParamsBean = ordersParamsList.get(i);
                if (ordersParamsBean == null) {
                    continue;
                }
                String column = ordersParamsBean.getColumn();
                boolean asc = ordersParamsBean.isAsc();
                if (TextUtils.isEmpty(column)) {
                    continue;
                }
                parmasMap.put("orders" + "[" + i + "].asc", asc);
                parmasMap.put("orders" + "[" + i + "].column", column);
            }
        }
        if (roomList != null && !roomList.isEmpty()) {
            for (int i = 0; i < roomList.size(); i++) {
                Integer integer = roomList.get(i);
                parmasMap.put("room" + "[" + i + "]", integer);
            }
        }
        if (labelList != null && !labelList.isEmpty()) {
            for (int i = 0; i < labelList.size(); i++) {
                Integer integer = labelList.get(i);
                parmasMap.put("label" + "[" + i + "]", integer);
            }
        }
        if (extraMap != null && !extraMap.isEmpty()) {
            parmasMap.putAll(extraMap);
        }
        return parmasMap;
    }

    /**
     * 排序
     */
    public static class OrdersParamsBean {
        private boolean asc;
        private String column;

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }
    }

}
