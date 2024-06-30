import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getIcCardOne = (params) => {
    return getRequest('/icCard/getOne', params)
}
export const getIcCardList = (params) => {
    return getRequest('/icCard/getByPage', params)
}
export const getIcCardCount = (params) => {
    return getRequest('/icCard/count', params)
}
export const addIcCard = (params) => {
    return postRequest('/icCard/insert', params)
}
export const editIcCard = (params) => {
    return postRequest('/icCard/update', params)
}
export const addOrEditIcCard = (params) => {
    return postRequest('/icCard/insertOrUpdate', params)
}
export const deleteIcCard = (params) => {
    return postRequest('/icCard/delByIds', params)
}
export const getUserList = (params) => {
    return getRequest('/superUser/getUserList', params)
}
export const rechargeIcCard = (params) => {
    return postRequest('/icCard/recharge', params)
}