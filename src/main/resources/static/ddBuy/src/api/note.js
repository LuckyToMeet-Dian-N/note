import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/listFile',
        method: 'get',
        params: query
    });
};

export const deleteNote = query => {
    return request({
        url: 'api/users/deleteNote',
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

export const usersUploadFile = query => {
    return request({
        url: 'api/users/usersUploadFile',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const updateFile = query => {
    return request({
        url: 'api/users/updateFile',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const deleteFile = query => {
    return request({
        url: 'api/users/deleteFile',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const createFile = query => {
    return request({
        url: 'api/users/createFile',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const sendEmail = query => {
    return request({
        url: 'api/users/sendEmail',
        method: 'post',
        data: qs.stringify(query)
    });
};

export const createNote = query => {
    return request({
        url: 'api/users/createNote',
        method: 'post',
        data: qs.stringify(query)
    });
};


export const updateNote = query => {
    return request({
        url: 'api/users/updateNote',
        method: 'post',
        data: qs.stringify(query)
    });
};
