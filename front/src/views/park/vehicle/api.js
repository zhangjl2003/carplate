import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getVehicleOne = (params) => {
    return getRequest('/vehicle/getOne', params)
}
export const getVehicleList = (params) => {
    return getRequest('/vehicle/getByPage', params)
}
export const getVehicleCount = (params) => {
    return getRequest('/vehicle/count', params)
}
export const addVehicle = (params) => {
    return postRequest('/vehicle/insert', params)
}
export const editVehicle = (params) => {
    return postRequest('/vehicle/update', params)
}
export const addOrEditVehicle = (params) => {
    return postRequest('/vehicle/insertOrUpdate', params)
}
export const deleteVehicle = (params) => {
    return postRequest('/vehicle/delByIds', params)
}
export const getUserList = (params) => {
    return getRequest('/superUser/getUserList', params)
}