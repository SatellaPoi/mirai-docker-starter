<div align="center">
   <img width="160" src="https://github.com/mamoe/mirai/blob/dev/docs/mirai.png" alt="logo"></br>

   <img width="95" src="https://github.com/mamoe/mirai/blob/dev/docs/mirai.svg" alt="title">

---

[![Gitter](https://badges.gitter.im/mamoe/mirai.svg)](https://gitter.im/mamoe/mirai?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Docker](https://github.com/SatellaPoi/mirai-docker-starter/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/SatellaPoi/mirai-docker-starter/actions/workflows/docker-publish.yml)

Mirai 是一个在全平台下运行，提供 QQ Android 和 TIM PC 协议支持的高效率机器人框架

这个项目的名字来源于

<p><a href = "http://www.kyotoanimation.co.jp/">京都动画</a>作品<a href = "https://zh.moegirl.org/zh-hans/%E5%A2%83%E7%95%8C%E7%9A%84%E5%BD%BC%E6%96%B9">《境界的彼方》</a>的<a href = "https://zh.moegirl.org/zh-hans/%E6%A0%97%E5%B1%B1%E6%9C%AA%E6%9D%A5">栗山未来(Kuriyama <b>Mirai</b>)</a></p>
<p><a href = "https://www.crypton.co.jp/">CRYPTON</a>以<a href = "https://www.crypton.co.jp/miku_eng">初音未来</a>为代表的创作与活动<a href = "https://magicalmirai.com/2019/index_en.html">(Magical <b>Mirai</b>)</a></p>
图标以及形象由画师<a href = "">DazeCake</a>绘制

</div>
<br/>

# mirai-http-docker

Mirai HTTP Docker 一键启动环境

<b>Mirai-API-http 插件 提供 HTTP API 供所有语言使用 mirai</b>

<b>这个项目旨在对想直接用`Mirai-API-http`进行开发的同学们直接启动 Mirai + HTTP 服务</b>

## 玩法

容器默认开 8080 端口，与 Mirai-HTTP-API 默认配置一致，有需要可以自己映射

使用 `docker-compose`启动：

* 克隆项目

```bash
git clone https://ghproxy.com/https://github.com/SatellaPoi/mirai-docker-starter.git <FLODER NAME>
```

* 第一次生成配置文件

```bash
docker-compose up -d
```

* 修改配置文件后重新启动

```bash
docker-compose restart
```

## 当前版本

| 插件                 | 版本   | 源代码 |
| -------------------- | ------ | ---- |
| mirai-console-loader | 2.1.2  | <https://github.com/iTXTech/mirai-console-loader> |
| mirai-http-api       | 2.5.2 | <https://github.com/project-mirai/mirai-api-http> |
| mirai-core-all       | 2.14.0  | <https://github.com/project-mirai/mirai-repo-mirror> |

## 限制

### docker

docker 是容器服务，因此端口开放但网络出口是 docker 的 bridge 环境。

建议编写好的项目直接使用 docker-compose 同网络调用

也可将 network 的 mode 设置为 host 使用主机网卡。

### console

console 启动的时候会检查更新，有需要可以调整`config.json`。

### 说明

项目中的 `mcl` 是来自官方 `mirai-console-loader` 的 `github release`， plugins 目录下的 `mirai-http-api` 来自 `github release` 。

大家可以随意添加自己需要的插件进去。
~
