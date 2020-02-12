import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/login',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const regist = query => {
    return request({
        url: 'api/users/register',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const getUserInfo = query => {
    return request({
        url: 'api/users/getUserInfo',
        method: 'get',
        param: query
    });
};

