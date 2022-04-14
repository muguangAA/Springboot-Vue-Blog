import request from '@/utils/request'


export function getTagList() {
  return request({
    url: '/guest/getTagList',
    method: 'get'
  })
}


export function getArticleById(id) {
  return request({
    url: '/guest/getArticleById/' + id,
    method: 'get',
  })
}

export function getPublishedArticleByTitle(title) {
  return request({
    url: '/guest/getPublishedArticleByTitle/' + title,
    method: 'get'
  })
}

export function getArticlesByTag(tag, pageNum, pageSize) {
  return request({
    url: '/guest/getArticlesByTag/' + tag,
    method: 'get',
    params: {
      "pageNum": pageNum,
      "pageSize":pageSize
    }
  })
}

export function getArticlesByPage(pageNum, pageSize) {
  return request({
    url: '/guest/getArticlesByPage',
    method: 'get',
    params: {
      "pageNum": pageNum,
      "pageSize":pageSize
    }
  })
}

export function getRecentArticleByPage(pageNum, pageSize) {
  return request({
    url: '/guest/getRecentArticleByPage',
    method: 'get',
    params: {
      "pageNum": pageNum,
      "pageSize": pageSize
    }
  })
}

export function getPublishedArticlesByPage(pageNum, pageSize) {
  return request({
    url: '/guest/getPublishedArticlesByPage',
    method: 'get',
    params: {
      "pageNum": pageNum,
      "pageSize":pageSize
    }
  })
}

export function getPerTagArticleNums(){
  return request({
    url: '/guest/getPerTagArticleNums/',
    method: 'get',
  })
}

export function getTagArticleNums(tagName){
  return request({
    url: '/guest/getPerTagArticleNums/' + tagName,
    method: 'get',
  })
}

export function searchTags(params){
  return request({
    url: '/guest/searchTags',
    method: 'get',
    params: params
  })
}

//.......................................


export function fileDelete(fileName) {
  return request({
    url: '/admin/fileDelete',
    method: 'delete',
    params: {
      'fileName': fileName
    }
  })
}

export function uploadArticleImage(formData){
  return request({
    url: '/admin/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
    }
  })
}



export function putArticle(data){
  return request({
    url: '/admin/putArticle',
    method: 'put',
    data: data,
  })
}

export function saveTemplate(data){
  return request({
    url: '/admin/saveTemplate',
    method: 'post',
    data: data,
  })
}

export function deleteArticle(data){
  return request({
    url: '/admin/deleteArticle',
    method: 'delete',
    data: data
  })
}

export function addTag(data){
  return request({
    url: '/admin/addTag',
    method: 'post',
    data: data
  })
}

export function deleteTag(data){
  return request({
    url: '/admin/deleteTag',
    method: 'delete',
    data: data
  })
}


export function putTag(data){
  return request({
    url: '/admin/putTag',
    method: 'put',
    data: data
  })
}

