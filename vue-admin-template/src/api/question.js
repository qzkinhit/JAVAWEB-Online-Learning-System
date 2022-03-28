import request from '@/utils/request'

export function getAll(){
    return request ({
        url: '/question/all',
        method: 'get',
    })
}
export function getAllw(){
  return request ({
      url: '/question/allw',
      method: 'get',
  })
}
export function deleteByID(id) {
    return request({
      url: '/question/delete',
      method: 'get',
      //  /teacher/delete?id=xxx
      params:{id}
    })
  }
  // {name:xx,age:xxx}
  export function add(question) {
    return request({
      url: '/question/add',
      method: 'post',
      data:question
    })
  }
  
  export function get(id) {
    return request({
      url: '/question/get',
      method: 'get',
      params:{id}
    })
  }
  export function update(question) {
    return request({
      url: '/question/update',
      method: 'post',
      data:question
    })
  }
  export function search(question) {
    return request({
      url: '/question/search',
      method: 'post',
      data:question
    })
  }
  export function searchw(question) {
    return request({
      url: '/question/searchw',
      method: 'post',
      data:question
    })
  }
  export function query(type, w) {
    return request({
      url: '/question/query',
      method: 'get',
      params:{type, w}
    })
  }
  export function queryw(type, w) {
    return request({
      url: '/question/queryw',
      method: 'get',
      params:{type, w}
    })
  }
  export function searchmy(cid) {
    return request({
      url: '/question/getquebycus',
      method: 'get',
      params:{cid}
    })
  }
  export function likeQuestion(id) {
    return request({
      url: '/question/like',
      method: 'get',
      params:{id}
    })
  }
  export function watchQuestion(id) {
    return request({
      url: '/question/watch',
      method: 'get',
      params:{id}
    })
  }
  export function replyQuestion(id) {
    return request({
      url: '/question/reply',
      method: 'get',
      params:{id}
    })
  }
