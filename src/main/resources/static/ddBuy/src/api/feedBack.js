import request from '../utils/request';
import qs from 'qs'

export const insertFeedBack = query => {
    return request({
        url: 'api/users/insertFeedBack',
        method: 'post',
        data: qs.stringify(query)
    });
};