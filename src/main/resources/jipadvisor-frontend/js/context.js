const context = {
}
context.install = function () {
  Object.defineProperty(Vue.prototype, 'context', {
    get () {return context}
  });
}