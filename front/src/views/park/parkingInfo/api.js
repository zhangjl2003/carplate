import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getParkingInfoOne = (params) => {
    return getRequest('/parkingInfo/getOne', params)
}
export const getParkingInfoList = (params) => {
    return getRequest('/parkingInfo/getByPage', params)
}
export const getParkingInfoCount = (params) => {
    return getRequest('/parkingInfo/count', params)
}
export const addParkingInfo = (params) => {
    return postRequest('/parkingInfo/insert', params)
}
export const editParkingInfo = (params) => {
    return postRequest('/parkingInfo/update', params)
}
export const addOrEditParkingInfo = (params) => {
    return postRequest('/parkingInfo/insertOrUpdate', params)
}
export const deleteParkingInfo = (params) => {
    return postRequest('/parkingInfo/delByIds', params)
}