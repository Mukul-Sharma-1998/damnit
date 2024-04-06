'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
//var createRoomBtn = document.querySelector('#createRoomBtn');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var roomId = null; // Ensure roomId is declared

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function generateRoomId() {
    roomId = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
    sessionStorage.setItem('roomId', roomId);
}

function captureRoomId(){
    roomId = document.querySelector('#roomId').value.trim();
    sessionStorage.setItem('roomId', roomId);
}


function connect(event) {
    console.log("sendMessage");
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError); // Provide onConnected callback
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the destination where you expect to receive messages
    stompClient.subscribe('/topic/' + roomId.trim(), onMessageReceived);
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var roomId = sessionStorage.getItem('roomId', roomId);
//    var roomId = "1234";

    if (stompClient && messageContent && roomId) {
        var codeMessage = {
            content: messageContent
        };
        stompClient.send("/app/codeShare/" + roomId, {}, JSON.stringify(codeMessage));

        messageInput.value = '';
    }

    event.preventDefault();
}





function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

//usernameForm.addEventListener('submit', connect, true); // Change to connect function
usernameForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior
    captureRoomId();
    connect();
});
//createRoomBtn.addEventListener('click', function(event) {
//     event.preventDefault(); // Prevent the default form submission behavior
//     generateRoomId();
//     connect();
//});
messageForm.addEventListener('submit', sendMessage, true);
