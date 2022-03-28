import request from '@/utils/request'

export function getAll(){
    return request ({
        url: '/answer/all',
        method: 'get',
    })
}
export function getAllw(){
  return request ({
      url: '/answer/allw',
      method: 'get',
  })
}
export function deleteByID(id) {
    return request({
      url: '/answer/delete',
      method: 'get',
      //  /teacher/delete?id=xxx
      params:{id}
    })
  }
  // {name:xx,age:xxx}
  export function add(answer) {
    return request({
      url: '/answer/add',
      method: 'post',
      data:answer
    })
  }
  
  export function get(id) {
    return request({
      url: '/answer/get',
      method: 'get',
      params:{id}
    })
  }
  export function update(answer) {
    return request({
      url: '/answer/update',
      method: 'post',
      data:answer
    })
  }
  export function getAnswerByQuestion(qid) {
    return request({
      url: '/answer/getansbyq',
      method: 'get',
      params:{qid}
    })
  }
  export function getAnswerByQuestionw(qid) {
    return request({
      url: '/answer/getansbyqw',
      method: 'get',
      params:{qid}
    })
  }
  export function getAnswerByCus(cid) {
    return request({
      url: '/answer/getansbycus',
      method: 'get',
      params:{cid}
    })
  }
  export function getAnswerByCusw(cid) {
    return request({
      url: '/answer/getansbycusw',
      method: 'get',
      params:{cid}
    })
  }
  export function likeAnswer(id) {
    return request({
      url: '/answer/like',
      method: 'get',
      params:{id}
    })
  }
  