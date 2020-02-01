import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/login',
        method: 'post',
         data: qs.stringify(query)
    });
};