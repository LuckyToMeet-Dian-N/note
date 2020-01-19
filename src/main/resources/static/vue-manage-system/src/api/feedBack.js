import request from '../utils/request';
import qs from 'qs'

export const fetchData = query => {
    return request({
        url: 'api/admin/selectFeedBack',
        method: 'get',
        params: query
    });
};

export const updateData = updateData => {
    return request({
        url: 'api/admin/updateFeedBacK',
        method: 'post',
        data: qs.stringify(updateData)
    });
};


export const deleteData = deleteData => {
    return request({
        url: 'api/admin/deleteFeedBacK',
        method: 'post',
        data: qs.stringify(deleteData)
    });
};
