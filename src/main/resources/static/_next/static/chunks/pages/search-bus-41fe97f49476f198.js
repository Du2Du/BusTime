(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[705],{5661:function(e,n,t){(window.__NEXT_P=window.__NEXT_P||[]).push(["/search-bus",function(){return t(9543)}])},5244:function(e,n,t){"use strict";t.d(n,{m:function(){return f}});var r=t(5893),i=t(1163),a=t.n(i),s=(t(7294),t(4568)),o=t(8929),l=t(8058),c=t(3459),u=t(3377),d=t(7658),m=t.n(d);function p(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function _(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{},r=Object.keys(t);"function"===typeof Object.getOwnPropertySymbols&&(r=r.concat(Object.getOwnPropertySymbols(t).filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable})))),r.forEach((function(n){p(e,n,t[n])}))}return e}function f(e){var n=arguments.length>1&&void 0!==arguments[1]&&arguments[1],t=arguments.length>2&&void 0!==arguments[2]&&arguments[2],a=function(a){var s=(0,o.S)(),l=s.userData,c=s.isAdmin,d=s.isSuperAdmin,m=(0,i.useRouter)().pathname,p=m===u.M.LOGIN||m===u.M.REGISTER||"/"===m;return p?l?(0,r.jsx)(h,{isCredential:p}):(0,r.jsx)(e,_({},a)):l?t?d?(0,r.jsx)(e,_({},a)):(0,r.jsx)(h,{isCredential:!0}):n?c?(0,r.jsx)(e,_({},a)):(0,r.jsx)(h,{isCredential:!0}):(0,r.jsx)(e,_({},a)):(0,r.jsx)(h,{isCredential:p})};return a}var h=function(e){var n=e.isCredential,t=(0,l.l)(),i=t.setTrue,o=t.setFalse;return(0,r.jsxs)(r.Fragment,{children:[(0,r.jsx)(s.Sd,{title:"N\xe3o Autorizado"}),(0,r.jsxs)("form",{onSubmit:function(e){e.preventDefault(),i(),a().push(n?u.M.HOME:u.M.LOGIN).finally(o)},className:"".concat(m().notAuth," rounded"),children:[(0,r.jsx)("p",{children:"Voc\xea n\xe3o pode acessar esse recurso "}),(0,r.jsx)(c.z,{label:"Voltar"})]})]})}},3459:function(e,n,t){"use strict";t.d(n,{z:function(){return u},M:function(){return d}});var r=t(5893),i=(t(7294),t(4931)),a=t(4568),s=t(2429),o=t.n(s),l=t(7041),c=t.n(l),u=function(e){var n=e.label,t=e.onClick;return(0,r.jsxs)("button",{type:"submit",onClick:t,className:c().buttonBorder,children:[(0,r.jsx)("span",{}),(0,r.jsx)("span",{}),(0,r.jsx)("span",{}),(0,r.jsx)("span",{}),n]})},d=function(e){var n=e.isLogin,t=e.label,s=e.register,l=e.errors,c=n?"ENTRAR":"CADASTRAR",d=["name","cpf","birthDate","email","password"],m={name:{required:"O nome \xe9 obrigat\xf3rio",pattern:"",min:""},cpf:{required:"O cpf \xe9 obrigat\xf3rio",pattern:"O cpf deve ficar no formato XXX.XXX.XXX-XX",min:""},birthDate:{required:"A data de nascimento \xe9 obrigat\xf3ria",pattern:"A data de nascimento deve ficar no formato dd/mm/yyyy",min:""},email:{required:"O email \xe9 obrigat\xf3rio",pattern:"",min:""},password:{required:"A senha \xe9 obrigat\xf3ria",pattern:"",min:"A senha deve ter no m\xednimo 6 d\xedgitos"}},p=function(e){i.ZP.error(e)};return(0,r.jsxs)(r.Fragment,{children:[(0,r.jsx)("h1",{className:"text-2xl",children:t}),!n&&(0,r.jsxs)(r.Fragment,{children:[(0,r.jsx)(a.II,{label:"Nome",placeHolder:"Nome",register:s("name",{required:!0}),id:"name",className:o().credentialsInput}),(0,r.jsx)(a.II,{label:"CPF",placeHolder:"CPF",register:s("cpf",{required:!0,pattern:/\d{3}\.\d{3}\.\d{3}\-\d{2}/g}),id:"cpf",className:o().credentialsInput}),(0,r.jsx)(a.II,{label:"Data de Nascimento",placeHolder:"Data de Nascimento",register:s("birthDate",{required:!0,pattern:/^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/}),id:"date",className:o().credentialsInput})]}),(0,r.jsx)(a.II,{label:"Email",placeHolder:"Email",register:s("email",{required:!0}),id:"email",type:"email",className:o().credentialsInput}),(0,r.jsx)(a.II,{label:"Senha",placeHolder:"Senha",id:"password",register:s("password",{required:!0,min:6}),className:o().credentialsInput,type:"password"}),(0,r.jsx)(u,{onClick:function(){d.forEach((function(e){var n,t,r;"required"===(null===(n=l[e])||void 0===n?void 0:n.type)&&p(m[e].required),"pattern"===(null===(t=l[e])||void 0===t?void 0:t.type)&&p(m[e].pattern),"min"===(null===(r=l[e])||void 0===r?void 0:r.type)&&p(m[e].min)}))},label:c})]})}},9543:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return f}});var r=t(5893),i=t(7294),a=t(4568),s=t(5244),o=t(1163),l=t(5621),c=t(1173),u=t(7962),d=t(2866),m=t(9621),p=t.n(m),_=function(){var e=(0,i.useState)(),n=e[0],t=e[1],s=(0,o.useRouter)().query.line;(0,i.useEffect)((function(){m()}),[s]);var m=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;c.z.get("".concat(l.M.FILTER_BUS,"?line=").concat(s,"&size=10&page=").concat(e)).then((function(e){return t(e.data)})).catch(u.x2)};return(0,r.jsxs)("div",{className:p().listAll,children:[(0,r.jsxs)("h1",{children:["Rotas da linha: ",s]}),0===(null===n||void 0===n?void 0:n.content.length)?(0,r.jsx)("h2",{className:"".concat(p().not," mt-3"),children:"Nenhuma Rota de \xd4nibus encontrado"}):n&&(0,r.jsxs)(r.Fragment,{children:[(0,r.jsx)("div",{className:p().list,children:n.content.map((function(e){return(0,r.jsx)(d.V,{bus:e},e.id)}))}),(0,r.jsx)(a.tl,{reloadItens:m,pagination:n,showTotal:!0})]})]})},f=(0,s.m)((function(){return(0,r.jsxs)("div",{className:p().searchBus,children:[(0,r.jsx)(a.Sd,{title:"Pesquisar"}),(0,r.jsx)(_,{})]})}))},7658:function(e){e.exports={notAuth:"withAuth_notAuth___86PU"}},2429:function(e){e.exports={container:"Credentials_container__5SmbT",credentialsInput:"Credentials_credentialsInput__gsHMg",credentials:"Credentials_credentials__Oc15W",formCredentials:"Credentials_formCredentials__Xa6ej",inputError:"Credentials_inputError__yp8il",label:"Credentials_label__XTi8k",redirect:"Credentials_redirect__PGnc6"}},7041:function(e){e.exports={buttonBorder:"Button_buttonBorder__AohEF","btn-anim1":"Button_btn-anim1__CyFmk","btn-anim2":"Button_btn-anim2__dW1Wl","btn-anim3":"Button_btn-anim3__3urRD","btn-anim4":"Button_btn-anim4___6mM2"}},9621:function(e){e.exports={not:"SearchBus_not__a4BhA",listAll:"SearchBus_listAll__ENRXM",list:"SearchBus_list__D4His",item:"SearchBus_item__fY7Ra"}}},function(e){e.O(0,[774,888,179],(function(){return n=5661,e(e.s=n);var n}));var n=e.O();_N_E=n}]);