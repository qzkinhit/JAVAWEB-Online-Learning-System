import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data:data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function getStu() {
  return request({
    url: '/stu/all',
    method: 'get'
  })
}
export function delStu(id) {
    return request({
      url: '/stu/del',
      method: 'get',
      params:{id}
    })
  }
  export function add(edu_stu) {
    return request({
      url: '/stu/add',
      method: 'post',
      data:edu_stu
    })
  }
  export function reg(edu_stu) {
    return request({
      url: '/stu/reg',
      method: 'post',
      data:edu_stu
    })
  }
  export function get(id) {
    return request({
      url: '/stu/get',
      method: 'get',
      params:{id}
    })
  }
  export function getStuById(id) {
    return request({
      url: '/stu/get',
      method: 'get',
      params:{id}
    })
  }
  export function update(edu_stu) {
    return request({
      url: '/stu/update',
      method: 'post',
      data:edu_stu
    })
  }


