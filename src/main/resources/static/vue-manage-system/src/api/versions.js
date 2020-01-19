import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/admin/selectAllVersions',
        method: 'get',
        params: query
    });
};

export const deleteData = deleteData => {
    return request({
        url: 'api/admin/deleteVersions',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};
