/**
 * use case for user-dao-sql.js
 */ 
var assert = require('assert'), 
    userDao = require('user-dao-sql'), 
    fs = require('fs');
    
process.on('uncaughtException', function(err) {
    console.err('================:' + err);
    finished();
    process.exit();
});
    
var test = module.exports = {};

test.setup = function(next) {
    return next();
};

test.teardown = function(next) {
    next();
};

test.hello = function() {
    /*
    fs.stat('/home/chang/12.wma', function() {
        console.log('i reached.');
        fs.stat('/home/chang/12.wma', function() {
            console.log('i will nerver reach.');
        });
        assert.ok(false, 'ok false');
    });*/
};

test.testInsertAndGet = function() {
    var user = {
        name : 'tom', 
        description : 'tom is a good boy.'
    };
    
    //test insert
    userDao.insert(user, function(err, res) {
        assert.isNull(err, 'fail to insert:' + err);
        assert.isNotNull(res, 'insert result is null.');
        assert.isDefined(res.insertId, 'insert result\'s id is null.');
        
        //test get and check insert result
        userDao.getById(res.insertId, function(err, user2) {
            assert.isNull(err, 'fail to get user with ' + res.insertId + ':' + err);
            assertUser(user, user2);
            
            //test update
            user2.description = 'tom is a bad boy.';
            userDao.update(user2, function(err, res2) {
                assert.isNull(err, 'fail to update user with ' + user2.id + ':' + err);
                
                //check update result
                userDao.getById(user2.id, function(err, user3) {
                    assert.isNull(err, 'fail to get user with ' + res.insertId + ':' + err);
                    assertUser(user2, user3);
                    
                    //test delete
                    userDao.deleteById(user2.id, function(err, res3) {
                        assert.isNull(err, 'fail to delete user with ' + user2.id + ':' + err);
                        
                        //check delete result 
                        userDao.getById(user2.id, function(err, user3) {
                            finished();
                            assert.isNull(err, 'fail to get user with ' + res.insertId + ':' + err);
                            assert.isNull(user3, 'user3 should be null.');
                        });
                    });
                });
            });
        });
    });
};

var assertUser = function(u1, u2) {
    if(u1 == u2) return;
    if(!u1) {
        assert.fail(false, true, 'u1 is null or undefined but u2 is not.');
        return;
    }
    
    if(!u2) {
        assert.fail(false, true, 'u2 is null or undefined but u1 is not.');
        return;
    }
    
    assert.equal(u1.name, u2.name, 'two user name is not the same:' + u1.name + ':' + u2.name);
    assert.equal(u1.description, u2.description, 'two user description is not the same.' + u1.description + ':' + u2.description);
};

var finished = function() {
    userDao.close();
};
