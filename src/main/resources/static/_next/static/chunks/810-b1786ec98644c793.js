(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[810],{5244:function(e,n,r){"use strict";r.d(n,{m:function(){return _}});var t=r(5893),i=r(1163),a=r.n(i),s=(r(7294),r(4568)),o=r(8929),c=r(8058),l=r(3459),u=r(3377),d=r(7658),m=r.n(d);function f(e,n,r){return n in e?Object.defineProperty(e,n,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[n]=r,e}function p(e){for(var n=1;n<arguments.length;n++){var r=null!=arguments[n]?arguments[n]:{},t=Object.keys(r);"function"===typeof Object.getOwnPropertySymbols&&(t=t.concat(Object.getOwnPropertySymbols(r).filter((function(e){return Object.getOwnPropertyDescriptor(r,e).enumerable})))),t.forEach((function(n){f(e,n,r[n])}))}return e}function _(e){var n=arguments.length>1&&void 0!==arguments[1]&&arguments[1],r=arguments.length>2&&void 0!==arguments[2]&&arguments[2],a=function(a){var s=(0,o.S)(),c=s.userData,l=s.isAdmin,d=s.isSuperAdmin,m=(0,i.useRouter)().pathname,f=m===u.M.LOGIN||m===u.M.REGISTER||"/"===m;return f?c?(0,t.jsx)(h,{isCredential:f}):(0,t.jsx)(e,p({},a)):c?r?d?(0,t.jsx)(e,p({},a)):(0,t.jsx)(h,{isCredential:!0}):n?l?(0,t.jsx)(e,p({},a)):(0,t.jsx)(h,{isCredential:!0}):(0,t.jsx)(e,p({},a)):(0,t.jsx)(h,{isCredential:f})};return a}var h=function(e){var n=e.isCredential,r=(0,c.l)(),i=r.setTrue,o=r.setFalse;return(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)(s.Sd,{title:"N\xe3o Autorizado"}),(0,t.jsxs)("form",{onSubmit:function(e){e.preventDefault(),i(),a().push(n?u.M.HOME:u.M.LOGIN).finally(o)},className:"".concat(m().notAuth," rounded"),children:[(0,t.jsx)("p",{children:"Voc\xea n\xe3o pode acessar esse recurso "}),(0,t.jsx)(l.z,{label:"Voltar"})]})]})}},3459:function(e,n,r){"use strict";r.d(n,{z:function(){return u},M:function(){return d}});var t=r(5893),i=(r(7294),r(4931)),a=r(4568),s=r(2429),o=r.n(s),c=r(7041),l=r.n(c),u=function(e){var n=e.label,r=e.onClick;return(0,t.jsxs)("button",{type:"submit",onClick:r,className:l().buttonBorder,children:[(0,t.jsx)("span",{}),(0,t.jsx)("span",{}),(0,t.jsx)("span",{}),(0,t.jsx)("span",{}),n]})},d=function(e){var n=e.isLogin,r=e.label,s=e.register,c=e.errors,l=n?"ENTRAR":"CADASTRAR",d=["name","cpf","birthDate","email","password"],m={name:{required:"O nome \xe9 obrigat\xf3rio",pattern:"",min:""},cpf:{required:"O cpf \xe9 obrigat\xf3rio",pattern:"O cpf deve ficar no formato XXX.XXX.XXX-XX",min:""},birthDate:{required:"A data de nascimento \xe9 obrigat\xf3ria",pattern:"A data de nascimento deve ficar no formato dd/mm/yyyy",min:""},email:{required:"O email \xe9 obrigat\xf3rio",pattern:"",min:""},password:{required:"A senha \xe9 obrigat\xf3ria",pattern:"",min:"A senha deve ter no m\xednimo 6 d\xedgitos"}},f=function(e){i.ZP.error(e)};return(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)("h1",{className:"text-2xl",children:r}),!n&&(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)(a.II,{label:"Nome",placeHolder:"Nome",register:s("name",{required:!0}),id:"name",className:o().credentialsInput}),(0,t.jsx)(a.II,{label:"CPF",placeHolder:"CPF",register:s("cpf",{required:!0,pattern:/\d{3}\.\d{3}\.\d{3}\-\d{2}/g}),id:"cpf",className:o().credentialsInput}),(0,t.jsx)(a.II,{label:"Data de Nascimento",placeHolder:"Data de Nascimento",register:s("birthDate",{required:!0,pattern:/^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/}),id:"date",className:o().credentialsInput})]}),(0,t.jsx)(a.II,{label:"Email",placeHolder:"Email",register:s("email",{required:!0}),id:"email",type:"email",className:o().credentialsInput}),(0,t.jsx)(a.II,{label:"Senha",placeHolder:"Senha",id:"password",register:s("password",{required:!0,min:6}),className:o().credentialsInput,type:"password"}),(0,t.jsx)(u,{onClick:function(){d.forEach((function(e){var n,r,t;"required"===(null===(n=c[e])||void 0===n?void 0:n.type)&&f(m[e].required),"pattern"===(null===(r=c[e])||void 0===r?void 0:r.type)&&f(m[e].pattern),"min"===(null===(t=c[e])||void 0===t?void 0:t.type)&&f(m[e].min)}))},label:l})]})}},6897:function(e,n,r){"use strict";r.d(n,{cN:function(){return m},W3:function(){return v},sY:function(){return P}});var t=r(5893),i=r(1664),a=r.n(i),s=r(7294),o=r(7536),c=r(3377),l=r(3459),u=r(2429),d=r.n(u),m=function(e){var n=e.onSubmit,r=e.isLogin,i=void 0!==r&&r,s=i?"Entrar":"Registrar",u=i?"N\xe3o tem cadastro? Cadastre Aqui":"J\xe1 tem login? Entre Aqui",m=(0,o.cI)(),f=m.register,p=m.handleSubmit,_=m.formState.errors;return(0,t.jsxs)("div",{className:d().credentials,children:[(0,t.jsx)("form",{onSubmit:p((function(e){if(!i)return n(e);var r=e.email,t=e.password;return n({email:r,password:t})})),className:"".concat(d().formCredentials," rounded"),children:(0,t.jsx)(l.M,{isLogin:i,label:s,errors:_,register:f})}),(0,t.jsx)("div",{className:d().redirect,children:(0,t.jsx)(a(),{replace:!0,href:i?c.M.REGISTER:c.M.LOGIN,children:u})})]})},f=r(5621),p=r(8058),_=r(1173),h=r(7962),b=r(6486),x=r(2064),j=r.n(x),g=function(e){var n=e.keys,r=e.permissionGroup;return(0,t.jsx)("main",{className:j().mainWrap,children:(0,t.jsx)("div",{className:j().mainInner,children:(0,t.jsx)("ul",{className:j().accor,children:(0,t.jsxs)("li",{children:[(0,t.jsx)("input",{type:"checkbox",name:"accordion",id:"off-accor".concat(n),className:j().offAccor}),(0,t.jsxs)("label",{htmlFor:"off-accor".concat(n),className:j().accorTitle,children:[(0,t.jsx)("span",{children:(0,b.get)({DEFAULT:"Passageiro",ADMINISTRATOR:"Administrador",SUPER_ADMINISTRATOR:"Super Administrador"},r.name)}),(0,t.jsx)("span",{className:j().icon})]}),0===r.permissionList.length?(0,t.jsx)("div",{className:j().accorContent,children:(0,t.jsx)("p",{children:"Nenhuma permiss\xe3o vinculada a esse perfil de usu\xe1rio"})}):r.permissionList.map((function(e){return(0,t.jsxs)("div",{className:j().accorContent,children:[(0,t.jsx)("p",{children:e.permissionName}),(0,t.jsx)("hr",{})]})}))]})})})})},v=function(){var e=(0,s.useState)([]),n=e[0],r=e[1],i=(0,p.l)(),a=i.setFalse,o=i.setTrue;return(0,s.useEffect)((function(){o(),_.z.get(f.M.LIST_GROUP_PERMISSIONS).then((function(e){return r(e.data)})).catch(h.x2).finally(a)}),[]),(0,t.jsxs)("div",{className:j().main,children:[(0,t.jsx)("h1",{children:"Listagem de Permiss\xf5es"}),n.map((function(e){return(0,t.jsx)(g,{permissionGroup:e,keys:e.id},e.id)}))]})},N=r(4568),I=function(e){var n=new Date(e),r=n.getFullYear(),t=n.getDate(),i=String(n.getMonth()+1),a=n.toLocaleTimeString();return"".concat(t,"/").concat(i,"/").concat(r," ").concat(a)},y=r(3328),S=r.n(y);function C(e,n,r){return n in e?Object.defineProperty(e,n,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[n]=r,e}function A(e){for(var n=1;n<arguments.length;n++){var r=null!=arguments[n]?arguments[n]:{},t=Object.keys(r);"function"===typeof Object.getOwnPropertySymbols&&(t=t.concat(Object.getOwnPropertySymbols(r).filter((function(e){return Object.getOwnPropertyDescriptor(r,e).enumerable})))),t.forEach((function(n){C(e,n,r[n])}))}return e}var O=[{label:"M\xe9todo",name:"method",size:1},{label:"Url",name:"url",size:3},{label:"Hora",name:"date",size:2},{label:"Status",name:"urlStatus",size:2},{label:"Usu\xe1rio",name:"userForm",renderColumn:function(e){if(null===e.userForm)return"";var n=e.userForm.split(","),r=n[1].slice(8,n[1].length-1),i=n[2].slice(4),a=n[3].slice(18,n[3].length-1);return(0,t.jsxs)("div",{className:"flex flex-col my-2",children:[(0,t.jsx)("p",{children:"ID: ".concat(i)}),(0,t.jsx)("p",{children:"Email: ".concat(r)}),(0,t.jsx)("p",{children:"Permiss\xe3o: ".concat(a)})]})},size:4}],P=function(e){e=null!==e?e:function(e){throw e}(new TypeError("Cannot destructure undefined"));var n=(0,s.useState)(),r=n[0],i=n[1],a=(0,p.l)(),o=a.setTrue,c=a.setFalse,l=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;o(),_.z.get("".concat(f.M.LOGS,"?page=").concat(e,"&size=10")).then((function(e){return i((function(){return A({},e.data,{content:e.data.content.map((function(e){return A({},e,{date:I(e.time)})}))})}))})).catch(h.x2).finally(c)};return(0,s.useEffect)((function(){l()}),[]),(0,t.jsxs)("div",{className:S().logs,children:[(0,t.jsx)(N.zx,{btnLabel:"Atualizar",onClick:function(){return l()},className:"mb-5 "}),r&&(0,t.jsx)("div",{className:S().tableLogs,children:(0,t.jsx)(N.iA,{headerTitle:"Logs",values:null===r||void 0===r?void 0:r.content,showPagination:!0,paginationData:r,reloadItens:l,columns:O})})]})}},7658:function(e){e.exports={notAuth:"withAuth_notAuth___86PU"}},2429:function(e){e.exports={container:"Credentials_container__5SmbT",credentialsInput:"Credentials_credentialsInput__gsHMg",credentials:"Credentials_credentials__Oc15W",formCredentials:"Credentials_formCredentials__Xa6ej",inputError:"Credentials_inputError__yp8il",label:"Credentials_label__XTi8k",redirect:"Credentials_redirect__PGnc6"}},7041:function(e){e.exports={buttonBorder:"Button_buttonBorder__AohEF","btn-anim1":"Button_btn-anim1__CyFmk","btn-anim2":"Button_btn-anim2__dW1Wl","btn-anim3":"Button_btn-anim3__3urRD","btn-anim4":"Button_btn-anim4___6mM2"}},3328:function(e){e.exports={logs:"Logs_logs__DgPAT",tableLogs:"Logs_tableLogs__h3a9z"}},2064:function(e){e.exports={main:"PermissionsListMain_main__r1_Kd",mainInner:"PermissionsListMain_mainInner__kIJo7",mainWrap:"PermissionsListMain_mainWrap__8mtAS",accor:"PermissionsListMain_accor__57Vct",accorTitle:"PermissionsListMain_accorTitle__CiFH4",icon:"PermissionsListMain_icon__gPIhs",accorContent:"PermissionsListMain_accorContent__Img89",offAccor:"PermissionsListMain_offAccor__6lXLp"}}}]);