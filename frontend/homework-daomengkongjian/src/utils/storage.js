// 以前 token 令牌，如果存到了本地，每一次都写这么长，太麻烦
// localStorage.setItem(键， 值)
// localStorage.getItem(键)
// localStorage.removeItem(键)

export const KEY = 'my-token-element-pc' // 存token
// 更好维护

export const setItem = (key, value) => {
  value && localStorage.setItem(key, JSON.stringify(value))
}

export const getItem = (key) => {
  return localStorage.getItem(key) && JSON.parse(localStorage.getItem(key))
}

export const removeItem = (key) => {
  localStorage.removeItem(key)
}
