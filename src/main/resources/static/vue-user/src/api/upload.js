import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/selectShareInfo',
        method: 'get',
        params: query
    });
};

export const updateWord = insertData => {
    return request({
        url: 'api/users/insertNoteByWord',
        method: 'post',
        data: qs.stringify(insertData)
    });
};