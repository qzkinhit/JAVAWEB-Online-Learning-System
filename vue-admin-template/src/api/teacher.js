import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/teacher/all',
    method: 'get',
  })
}

export function deleteByID(id) {
  return request({
    url: '/teacher/delete',
    method: 'get',
    //  /teacher/delete?id=xxx
    params:{id}
  })
}
// {name:xx,age:xxx}
export function add(teacher) {
  return request({
    url: '/teacher/add',
    method: 'post',
    data:teacher
  })
}

export function search(name){
  return request({
    url: 'teacher/search',
    method: 'get',
    params:{name}
  })
}

export function get(id) {
  return request({
    url: '/teacher/get',
    method: 'get',
    params:{id}
  })
}

export function update(teacher) {
  return request({
    url: '/teacher/update',
    method: 'post',
    data:teacher
  })
}