import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/admin/listOrders',
        method: 'get',
        params: query
    });
};

export const updateData = updateData => {
    return request({
        url: 'api/admin/updateOrders',
        method: 'post',
        data: qs.stringify(updateData)
    });
};


export const deleteData = deleteData => {
    return request({
        url: 'api/admin/deleteOrders',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};
