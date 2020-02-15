import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/getOrderList',
        method: 'get',
        params: query
    });
};

export const createOrders = query => {
    return request({
        url: 'api/users/createOrders',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const updateOrderStatus = query => {
    return request({
        url: 'api/users/updateOrderStatus',
        method: 'post',
        data: qs.stringify(query)
    });
};

