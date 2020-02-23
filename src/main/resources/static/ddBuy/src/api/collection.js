import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/getAllCollections',
        method: 'get',
        params: query
    });
};

export const deleteCollection = query => {
    return request({
        url: 'api/users/deleteCollection',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const insertCollection = query => {
    return request({
        url: 'api/users/insertCollection',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const allCollections = query => {
    return request({
        url: 'api/users/getAllCollections',
        method: 'get',
        params: query
    });
};
