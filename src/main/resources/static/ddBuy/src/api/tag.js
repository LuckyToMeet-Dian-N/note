import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/selectLabel',
        method: 'get',
        params: query
    });
};

export const deleteTag = query => {
    return request({
        url: 'api/users/deleteLabel',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const updateLabel = query => {
    return request({
        url: 'api/users/updateLabel',
        method: 'post',
        data: qs.stringify(query)
    });
};
export const insertLabel = query => {
    return request({
        url: 'api/users/insertLabel',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const fetchDataByNoteId = query => {
    return request({
        url: 'api/users/selectNoteByLabelId',
        method: 'get',
        params: query
    });
};

export const addLabelByNote = query => {
    return request({
        url: 'api/users/addLabelByNote',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const deleteLabelByNote = query => {
    return request({
        url: 'api/users/deleteLabelByNote',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const getVersionInfo = query => {
    return request({
        url: 'api/users/versionInfo',
        method: 'get',
        params: query
    });
};