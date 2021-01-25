const {elasticClient} = require('./elastic_client')


var addPost = async function(post){
    return await elasticClient.index({
        index: "posts",
        type: "post_mapping",
        id: post.id,
        body: post
    })
}

var addEvent = async function(event){
    return await elasticClient.index({
        index: "events",
        type: "event_mapping",
        id: event.id,
        body: event
    })
}


var updatePost = async function(post){
    await deletePost(post.id)
    await addPost(post)
   
}

var updateEvent = async function(event){
    await deleteEvent(event.id)
    await addEvent(event)
   
}

var deletePost = async function(postId){
    return await elasticClient.delete({
        index: "posts",
        type: "post_mapping",
        id: String(postId)
    })
}

var deleteEvent = async function(eventId){
    return await elasticClient.delete({
        index: "events",
        type: "event_mapping",
        id: String(eventId)
    })
}


var searchPost = async function(query_word){
    titleResult =  await elasticClient.search({
        index: "posts",
        type: "post_mapping",
        body: {
            query: {
                match: {
                    'title': query_word
                }
            }
        }
    })

    titleResult = titleResult.hits.hits.map(hit => hit._source)

    summaryResult =  await elasticClient.search({
        index: "posts",
        type: "post_mapping",
        body: {
            query: {
                match: {
                    'summary': query_word
                }
            }
        }
    })
    
    concatVersion = summaryResult.hits.hits.map(hit => hit._source).concat(titleResult)
    uniqueSet = new Set(concatVersion.map(res => JSON.stringify(res)))
    result = Array.from(uniqueSet).map(res => JSON.parse(res))
    return result
}

var searchEvent = async function(query_word){
    titleResult =  await elasticClient.search({
        index: "events",
        type: "event_mapping",
        body: {
            query: {
                match: {
                    'title': query_word
                }
            }
        }
    })

    titleResult = titleResult.hits.hits.map(hit => hit._source)

    bodyResult =  await elasticClient.search({
        index: "events",
        type: "event_mapping",
        body: {
            query: {
                match: {
                    'body': query_word
                }
            }
        }
    })
    
    concatVersion = bodyResult.hits.hits.map(hit => hit._source).concat(titleResult)
    uniqueSet = new Set(concatVersion.map(res => JSON.stringify(res)))
    result = Array.from(uniqueSet).map(res => JSON.parse(res))
    return result
}

module.exports = {
    addPost,
    addEvent,
    searchPost,
    searchEvent,
    deletePost,
    deleteEvent,
    updatePost,
    updateEvent,

}