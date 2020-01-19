import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/admin/listUsers',
        method: 'get',
        params: query
    });
};

export const updateData = updateData => {
    return request({
        url: 'api/admin/updateUserInfo',
        method: 'post',
        data: qs.stringify(updateData)
    });
};


export const addData = addData => {
    return request({
        url: 'api/admin/addUsers',
        method: 'post',
        data: qs.stringify(addData)
    });
};

export const deleteData = deleteData => {
    return request({
        url: 'api/admin/deleteUsers',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};

export const fetchConsoleData = query => {
    return request({
        url: 'api/admin/allInfo',
        method: 'get',
        params: query
    });
};