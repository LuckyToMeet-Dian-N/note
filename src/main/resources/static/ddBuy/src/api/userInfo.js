import request from '../utils/request';
import qs from 'qs'

export const updateUserInfo = query => {
    return request({
        url: 'api/users/updateUserInfo',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const updatePassword = query => {
    return request({
        url: 'api/users/updatePassword',
        method: 'post',
        data: qs.stringify(query)
    });
};

