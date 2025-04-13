# NICE: Network Intrusion Detection and Countermeasure Selection

NICE is an intelligent security framework designed for **virtualized cloud networks**. It uses lightweight agents on client nodes to monitor, detect, and respond to threats like DDoS attacks in real-time. By dynamically adapting its defense mechanisms, NICE enhances security and resilience across distributed cloud environments.

---

## 🔐 Features

- 📡 **Real-Time Intrusion Detection**  
  Monitors network traffic from multiple nodes using socket-based communication.

- 🛡️ **Dynamic Countermeasure Selection**  
  Blocks IPs triggering multiple suspicious requests (e.g., more than 3 downloads).

- 🧠 **Cloud-Based Decision Making**  
  Intrusion analysis and action are performed at the cloud server level.

- 📊 **Visual Monitoring**  
  Uses Java-based charting tools to visualize traffic and intrusion metrics.

---

## 🖥️ User Interface

### 📥 Client Interface  
Enables users to download resources securely from the cloud.

![Client Interface](https://github.com/user-attachments/assets/07318973-4b81-4a98-a35f-aa172762f621)

### ☁️ Cloud Server Interface  
Monitors resource requests and blocks malicious activity.

![Cloud Server](https://github.com/user-attachments/assets/be64bc3a-184f-42a4-8f1f-8a9d4b3531e0)

### 🧠 Detection in Action  
If more than 3 requests are made from the same IP, it is automatically blocked and a fake file is sent in response.

![How the Project Works](https://github.com/user-attachments/assets/beedbfd8-7252-4d35-b156-ae6d7206372a)

---

## ⚙️ Tech Stack

- Java (Swing UI, Sockets)
- Custom Thread & VM Management
- Server & Node modules for full communication loop

---

## 📬 Want the Full Project?

For the complete project source code and setup instructions, reach out via email:  
📧 **rahulroxx2002@gmail.com**

> **Note**: This project is shared for educational and research purposes only.

---
