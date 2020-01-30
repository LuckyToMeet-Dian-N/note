import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/getUserInfo',
        method: 'get',
        params: query
    });
};

export const updateData = updateData => {
    return request({
        url: 'api/users/updateUserInfo',
        method: 'post',
        data: qs.stringify(updateData)
    });
};