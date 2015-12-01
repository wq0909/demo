FROM 106.2.33.84:5000/nce2/debin:7.8

ADD compressed /2968489467@qq.com/2968489467@qq.com/nodejs01

COPY . /2968489467@qq.com/2968489467@qq.com/nodejs01

WORKDIR /2968489467@qq.com/2968489467@qq.com/nodejs01

#base installed cnpm
#RUN npm install -g cnpm --registry=https://r.cnpmjs.org

RUN cnpm install -d

CMD ["node","app.js"]