import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getParkingDataOne = (params) => {
    return getRequest('/parkingData/getOne', params)
}
export const getParkingDataList = (params) => {
    return getRequest('/parkingData/getByPage', params)
}
export const getParkingDataCount = (params) => {
    return getRequest('/parkingData/count', params)
}
export const addParkingData = (params) => {
    return postRequest('/parkingData/insert', params)
}
export const editParkingData = (params) => {
    return postRequest('/parkingData/update', params)
}
export const addOrEditParkingData = (params) => {
    return postRequest('/parkingData/insertOrUpdate', params)
}
export const deleteParkingData = (params) => {
    return postRequest('/parkingData/delByIds', params)
}
export const getVehicleList = (params) => {
    return getRequest('/vehicle/getAll', params)
}