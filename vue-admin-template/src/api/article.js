import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/article/all',
    method: 'get',
  })
}

export function deleteByID(id) {
  return request({
    url: '/article/delete',
    method: 'get',
    //  /teacher/delete?id=xxx
    params:{id}
  })
}
// {name:xx,age:xxx}
export function add(article) {
  return request({
    url: '/article/add',
    method: 'post',
    data:article
  })
}

export function get(id) {
  return request({
    url: '/article/get',
    method: 'get',
    params:{id}
  })
}

export function update(article) {
  return request({
    url: '/article/update',
    method: 'post',
    data:article
  })
}

export function search(article) {
  return request({
    url: '/article/search',
    method: 'post',
    data:article
  })
}

export function likeArticle(id) {
  return request({
    url: '/article/like',
    method: 'get',
    params:{id}
  })
}

export function replyArticle(id) {
  return request({
    url: '/article/reply',
    method: 'get',
    params:{id}
  })
}

export function watchArticle(id) {
  return request({
    url: '/article/watch',
    method: 'get',
    params:{id}
  })
}

export function getBySort(sort) {
  return request({
    url: '/article/sort',
    method: 'get',
    params:{sort}
  })
}

export function query(type, w) {
  return request({
    url: '/article/query',
    method: 'get',
    params:{type, w}
  })
}