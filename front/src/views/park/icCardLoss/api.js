import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getIcCardLossOne = (params) => {
    return getRequest('/icCardLoss/getOne', params)
}
export const getIcCardLossList = (params) => {
    return getRequest('/icCardLoss/getByPage', params)
}
export const getIcCardLossCount = (params) => {
    return getRequest('/icCardLoss/count', params)
}
export const addIcCardLoss = (params) => {
    return postRequest('/icCardLoss/insert', params)
}
export const editIcCardLoss = (params) => {
    return postRequest('/icCardLoss/update', params)
}
export const addOrEditIcCardLoss = (params) => {
    return postRequest('/icCardLoss/insertOrUpdate', params)
}
export const deleteIcCardLoss = (params) => {
    return postRequest('/icCardLoss/delByIds', params)
}
export const getIcCardList = (params) => {
    return getRequest('/icCard/getAll', params)
}
export const auditIcCardLoss = (params) => {
    return postRequest('/icCardLoss/audit', params)
}