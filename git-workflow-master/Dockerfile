FROM 106.2.33.84:5000/nce2/debin:7.8

# Install basic applications
# RUN apt-get install -y tar git curl nano wget dialog net-tools build-essential

# Install Python and Basic Python Tools
# RUN apt-get install -y python python-dev python-distribute python-pip libpq-dev

ADD compressed /2968489467@qq.com/2968489467@qq.com/python

COPY . /2968489467@qq.com/2968489467@qq.com/python

# Get pip to download and install requirements:
RUN pip install -r //2968489467@qq.com/2968489467@qq.com/python/requirements.txt

WORKDIR /2968489467@qq.com/2968489467@qq.com/python

CMD python manage.py runserver 0.0.0.0:80
