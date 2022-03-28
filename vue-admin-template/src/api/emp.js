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

export function getEmps() {
  return request({
    url: '/emp/all',
    method: 'get'
  })
}
export function delEmps(id) {
    return request({
      url: '/emp/del',
      method: 'get',
      params:{id}
    })
  }
  export function add(emp) {
    return request({
      url: '/emp/add',
      method: 'post',
      data:emp
    })
  }
  export function get(id) {
    return request({
      url: '/emp/get',
      method: 'get',
      params:{id}
    })
  }
  
  export function update(emp) {
    return request({
      url: '/emp/update',
      method: 'post',
      data:emp
    })
  }


