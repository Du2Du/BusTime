(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[229],{7183:function(e,t,n){(window.__NEXT_P=window.__NEXT_P||[]).push(["/home",function(){return n(6018)}])},5244:function(e,t,n){"use strict";n.d(t,{m:function(){return x}});var i=n(5893),a=n(1163),r=n.n(a);n(7294);var s=n(8744),l=n(8929),d=n(8058),o=n(3459),c=n(3377),u=n(7658),m=n.n(u);function x(e){let t=arguments.length>1&&void 0!==arguments[1]&&arguments[1],n=arguments.length>2&&void 0!==arguments[2]&&arguments[2];return function(r){let{userData:s,isAdmin:d,isSuperAdmin:o}=(0,l.S)(),{pathname:u}=(0,a.useRouter)(),m=u===c.M.LOGIN||u===c.M.REGISTER||"/"===u;return m?s?(0,i.jsx)(p,{isCredential:m}):(0,i.jsx)(e,{...r}):s?n?o?(0,i.jsx)(e,{...r}):(0,i.jsx)(p,{isCredential:!0}):t?d?(0,i.jsx)(e,{...r}):(0,i.jsx)(p,{isCredential:!0}):(0,i.jsx)(e,{...r}):(0,i.jsx)(p,{isCredential:m})}}let p=e=>{let{isCredential:t}=e,{setTrue:n,setFalse:a}=(0,d.l)(),l=e=>{e.preventDefault(),n(),r().push(t?c.M.HOME:c.M.LOGIN).finally(a)};return(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(s.Sd,{title:"N\xe3o Autorizado"}),(0,i.jsxs)("form",{onSubmit:l,className:"".concat(m().notAuth," rounded"),children:[(0,i.jsx)("p",{children:"Voc\xea n\xe3o pode acessar esse recurso "}),(0,i.jsx)(o.z,{label:"Voltar"})]})]})}},3459:function(e,t,n){"use strict";n.d(t,{z:function(){return c},M:function(){return u}});var i=n(5893);n(7294);var a=n(4931),r=n(8744),s=n(2429),l=n.n(s),d=n(7041),o=n.n(d);let c=e=>{let{label:t,onClick:n}=e;return(0,i.jsxs)("button",{type:"submit",onClick:n,className:o().buttonBorder,children:[(0,i.jsx)("span",{}),(0,i.jsx)("span",{}),(0,i.jsx)("span",{}),(0,i.jsx)("span",{}),t]})},u=e=>{let{isLogin:t,label:n,register:s,errors:d}=e,o=["name","cpf","birthDate","email","password"],u={name:{required:"O nome \xe9 obrigat\xf3rio",pattern:"",min:""},cpf:{required:"O cpf \xe9 obrigat\xf3rio",pattern:"O cpf deve ficar no formato XXX.XXX.XXX-XX",min:""},birthDate:{required:"A data de nascimento \xe9 obrigat\xf3ria",pattern:"A data de nascimento deve ficar no formato dd/mm/yyyy",min:""},email:{required:"O email \xe9 obrigat\xf3rio",pattern:"",min:""},password:{required:"A senha \xe9 obrigat\xf3ria",pattern:"",min:"A senha deve ter no m\xednimo 6 d\xedgitos"}},m=e=>{a.ZP.error(e)};return(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)("h1",{className:"text-2xl",children:n}),!t&&(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(r.II,{label:"Nome",placeHolder:"Nome",register:s("name",{required:!0}),id:"name",className:l().credentialsInput}),(0,i.jsx)(r.II,{label:"CPF",placeHolder:"CPF",register:s("cpf",{required:!0,pattern:/\d{3}\.\d{3}\.\d{3}\-\d{2}/g}),id:"cpf",className:l().credentialsInput}),(0,i.jsx)(r.II,{label:"Data de Nascimento",placeHolder:"Data de Nascimento",register:s("birthDate",{required:!0,pattern:/^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/}),id:"date",className:l().credentialsInput})]}),(0,i.jsx)(r.II,{label:"Email",placeHolder:"Email",register:s("email",{required:!0}),id:"email",type:"email",className:l().credentialsInput}),(0,i.jsx)(r.II,{label:"Senha",placeHolder:"Senha",id:"password",register:s("password",{required:!0,min:6}),className:l().credentialsInput,type:"password"}),(0,i.jsx)(c,{onClick(){o.forEach(e=>{var t,n,i;(null===(t=d[e])||void 0===t?void 0:t.type)==="required"&&m(u[e].required),(null===(n=d[e])||void 0===n?void 0:n.type)==="pattern"&&m(u[e].pattern),(null===(i=d[e])||void 0===i?void 0:i.type)==="min"&&m(u[e].min)})},label:t?"ENTRAR":"CADASTRAR"})]})}},2866:function(e,t,n){"use strict";n.d(t,{V:function(){return p}});var i=n(5893),a=n(7294),r=n(4931),s=n(8193),l=n(5621),d=n(8929),o=n(8058),c=n(1173),u=n(7962),m=n(4959),x=n.n(m);let p=e=>{let{bus:t,setFavoriteBus:n}=e,{setTrue:m,setFalse:p}=(0,o.l)(),{userData:f,getUser:h}=(0,d.S)(),[j,_]=(0,a.useState)(null==f?void 0:f.favoriteBus.find(e=>t.id===e.id)),b=function(){let e=!(arguments.length>0)||void 0===arguments[0]||arguments[0];return()=>{m(),c.z.get("".concat(e?l.M.FAVORITE_BUS:l.M.DISFAVOR_BUS,"/").concat(t.id)).then(i=>{n&&n(i.data),_(i.data.find(e=>t.id===e.id)),r.ZP.success("\xd4nibus ".concat(e?"favoritado":"desfavoritado"," com sucesso!")),h()}).catch(u.x2).finally(p)}};return(0,i.jsxs)("div",{className:x().listItem,children:[(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"Linha: "}),t.lineBus.lineName]}),(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"Hor\xe1rio: "}),t.hour]}),(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"Passagem: "}),"R$",t.ticketPrice]}),(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"Rota Incial: "}),t.inicialRoute]}),(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"Rota Final: "}),t.finalRoute]}),(0,i.jsxs)("div",{className:x().fieldItem,children:[(0,i.jsx)("b",{children:"N\xfamero: "}),t.busNumber]}),(0,i.jsx)("div",{className:x().fieldItem,children:j?(0,i.jsx)(s.AiFillHeart,{size:30,className:"cursor-pointer",onClick:b(!1)}):(0,i.jsx)(s.AiOutlineHeart,{className:"cursor-pointer",size:30,onClick:b()})})]})}},6018:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return _}});var i=n(5893),a=n(1163),r=n.n(a),s=n(7294),l=n(8744),d=n(8929),o=n(5244),c=n(5621),u=n(3377),m=n(1173),x=n(4959),p=n.n(x),f=n(2866);let h=()=>{let[e,t]=(0,s.useState)(),{isAdmin:n}=(0,d.S)(),a=()=>{r().push(u.M.CREATE_BUS)},o=function(e){let n=arguments.length>1&&void 0!==arguments[1]?arguments[1]:4;m.z.get("".concat(c.M.LIST_BUS,"?size=").concat(n,"&page=").concat(e)).then(e=>t(e.data))};return(0,s.useEffect)(()=>{m.z.get("".concat(c.M.LIST_BUS,"?size=4&page=0")).then(e=>t(e.data))},[]),(0,i.jsxs)("main",{className:p().homeMain,children:[n?(0,i.jsxs)("div",{className:p().busDescription,children:[(0,i.jsx)("p",{children:"\xc9 respons\xe1vel por alguma rota de \xf4nibus? Registre ela agora!"}),(0,i.jsx)(l.zx,{onClick:a,className:"my-4",btnLabel:"Cadastrar"})]}):null,(0,i.jsxs)("div",{className:p().busList,children:[(0,i.jsx)("p",{children:"Veja agora os hor\xe1rios de \xf4nibus:"}),(null==e?void 0:e.content.length)!==0&&(null==e?void 0:e.content)?(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)("div",{className:p().list,children:null==e?void 0:e.content.map(e=>(0,i.jsx)(f.V,{bus:e},e.id))}),e&&(0,i.jsx)(l.tl,{showTotal:!0,reloadItens:o,pagination:e})]}):(0,i.jsx)("p",{style:{color:"#fff"}})]})]})},j=(0,o.m)(()=>{let{userData:e}=(0,d.S)();return(0,s.useEffect)(()=>{e||r().reload()},[e]),(0,i.jsxs)("div",{className:p().home,children:[(0,i.jsx)(l.Sd,{title:"Home"}),(0,i.jsx)(h,{})]})});var _=j},7658:function(e){e.exports={notAuth:"withAuth_notAuth___86PU"}},2429:function(e){e.exports={container:"Credentials_container__5SmbT",credentialsInput:"Credentials_credentialsInput__gsHMg",credentials:"Credentials_credentials__Oc15W",formCredentials:"Credentials_formCredentials__Xa6ej",inputError:"Credentials_inputError__yp8il",label:"Credentials_label__XTi8k",redirect:"Credentials_redirect__PGnc6"}},7041:function(e){e.exports={buttonBorder:"Button_buttonBorder__AohEF","btn-anim1":"Button_btn-anim1__CyFmk","btn-anim2":"Button_btn-anim2__dW1Wl","btn-anim3":"Button_btn-anim3__3urRD","btn-anim4":"Button_btn-anim4___6mM2"}}},function(e){e.O(0,[774,888,179],function(){return e(e.s=7183)}),_N_E=e.O()}]);