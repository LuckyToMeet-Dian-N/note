import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/admin/getAllShare',
        method: 'get',
        params: query
    });
};

export const deleteData = deleteData => {
    return request({
        url: 'api/admin/deleteShare',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};
