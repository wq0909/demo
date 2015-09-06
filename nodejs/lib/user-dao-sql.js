/**
 * mysql dao for User
 */ 

var Client = require('mysql').Client, 
    opt = require('../config/db.js');
    
var conn = null;

var UserDao = module.exports = {};

/**
 * get db connectioin
 */ 
var getConnection = function() {
    if(!!conn) return conn;
    conn = new Client();
    conn.host = opt.host;
    conn.port = opt.port;
    conn.user = opt.username;
    conn.password = opt.password;
    conn.database = opt.database;
	conn.connect();
    return conn;
};

/**
 * close db connection
 */ 
var closeConnection = function() {
    if(!conn) return;
    conn.end();
    conn = null;
};

/**
 * get user by id
 * 
 * @param id: user id
 * @param cb: callback function with two params: function(err, res). if id not exist, res will be null.
 */ 
UserDao.getById = function(id, cb) {
    if(!id) {
        throw new Error('invalid argumenet id:' + id);
    }
    var conn = getConnection();
    conn.query('select * from User where id=?', [id], function(err, res) {
        if(!!cb && typeof(cb) == 'function') {
            if(res.length >= 1) {
                cb(err, res[0]);
            } else {
                cb(err, null);
            }
        }
    });
};

/**
 * insert user
 * 
 * @param user: user info
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.insert = function(user, cb) {
    if(!user) {
        throw new Error('invalid argumenet user:' + user);
    }
    var conn = getConnection();
    conn.query('insert into User(name, description) values(?, ?)', [user.name, user.description], function(err, res) {
        if(!!cb && typeof(cb) == 'function') {
            cb(err, res);
        }
    });
};

/**
 * update user
 * 
 * @param user: user info
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.update = function(user, cb) {
    if(!user) {
        throw new Error('invalid argumenet user:' + user);
    }
    var conn = getConnection();
    conn.query('update User set name=?, description=? where id=?', [user.name, user.description, user.id], function(err, res) {
        if(!!cb && typeof(cb) == 'function') {
            cb(err, res);
        }
    });
};

/**
 * delete user by id
 * 
 * @param id: user id
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.deleteById = function(id, cb) {
    if(!id) {
        throw new Error('invalid argumenet id:' + id);
    }
    var conn = getConnection();
    conn.query('delete from User where id=?', [id], function(err, res) {
        if(!!cb && typeof(cb) == 'function') {
            cb(err, res);
        }
    });
};

UserDao.close = function() {
    closeConnection();
};