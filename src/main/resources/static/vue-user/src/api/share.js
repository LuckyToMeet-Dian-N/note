import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/selectShareInfo',
        method: 'get',
        params: query
    });
};

export const deleteData = deleteData => {
    return request({
        url: 'api/users/deleteShare',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};

export const insertData = insertData => {
    return request({
        url: 'api/users/insertShare',
        method: 'post',
        data: qs.stringify(insertData)
    });
};