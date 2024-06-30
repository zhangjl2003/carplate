import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getFeeScaleOne = (params) => {
    return getRequest('/feeScale/getOne', params)
}
export const getFeeScaleList = (params) => {
    return getRequest('/feeScale/getByPage', params)
}
export const getFeeScaleCount = (params) => {
    return getRequest('/feeScale/count', params)
}
export const addFeeScale = (params) => {
    return postRequest('/feeScale/insert', params)
}
export const editFeeScale = (params) => {
    return postRequest('/feeScale/update', params)
}
export const addOrEditFeeScale = (params) => {
    return postRequest('/feeScale/insertOrUpdate', params)
}
export const deleteFeeScale = (params) => {
    return postRequest('/feeScale/delByIds', params)
}