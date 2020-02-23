import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/selectShareInfo',
        method: 'get',
        params: query
    });
};
export const insertShare = query => {
    return request({
        url: 'api/users/insertShare',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const getUserInfo = query => {
    return request({
        url: 'api/users/getUserInfo',
        method: 'get',
        params: query
    });
};

export const allShare = query => {
    return request({
        url: 'api/users/selectShareInfo',
        method: 'get',
        params: query
    });
};