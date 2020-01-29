import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/getAllCollections',
        method: 'get',
        params: query
    });
};
export const deleteData = deleteData => {
    return request({
        url: 'api/users/deleteCollection',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};

export const addDataByCollection = addData => {
    return request({
        url: 'api/users/insertCollection',
        method: 'post',
        data: qs.stringify(addData)
    });
};