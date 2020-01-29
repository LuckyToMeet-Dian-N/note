import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/users/listFile',
        method: 'get',
        params: query
    });
};
export const updateData = updateData => {
    return request({
        url: 'api/users/updateFile',
        method: 'post',
        data: qs.stringify(updateData)
    });
};

export const addData = addData => {
    return request({
        url: 'api/users/createFile',
        method: 'post',
        data: qs.stringify(addData)
    });
};

export const deleteData = deleteData => {
    return request({
        url: 'api/users/deleteFile',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};

export const fetchNoteList = query => {
    return request({
        url: 'api/users/selectNote',
        method: 'get',
        params: query
    });
};

export const fetchNoteListByLabelId = query => {
    return request({
        url: 'api/users/selectNoteByLabelId',
        method: 'get',
        params: query
    });
};

export const addLabelByNote = data => {
    return request({
        url: 'api/users/addLabelByNote',
        method: 'post',
        data: qs.stringify(data)
    });
};

export const deleteLabelByNoteData = deleteData => {
    return request({
        url: 'api/users/deleteLabelByNote',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};

export const createNote = createNoteData => {
    return request({
        url: 'api/users/createNote',
        method: 'post',
        data: qs.stringify(createNoteData)
    });
};

export const deleteNote = createNoteData => {
    return request({
        url: 'api/users/deleteNote',
        method: 'post',
        data: qs.stringify(createNoteData)
    });
};

export const updateNote  = data => {
    return request({
        url: 'api/users/updateNote',
        method: 'post',
        data: qs.stringify(data)
    });
};

