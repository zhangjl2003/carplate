import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getParkingSpaceOne = (params) => {
    return getRequest('/parkingSpace/getOne', params)
}
export const getParkingSpaceList = (params) => {
    return getRequest('/parkingSpace/getByPage', params)
}
export const getParkingSpaceCount = (params) => {
    return getRequest('/parkingSpace/count', params)
}
export const addParkingSpace = (params) => {
    return postRequest('/parkingSpace/insert', params)
}
export const editParkingSpace = (params) => {
    return postRequest('/parkingSpace/update', params)
}
export const addOrEditParkingSpace = (params) => {
    return postRequest('/parkingSpace/insertOrUpdate', params)
}
export const deleteParkingSpace = (params) => {
    return postRequest('/parkingSpace/delByIds', params)
}