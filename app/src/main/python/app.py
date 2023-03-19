import requests

def fun(TeacherID, Video ,Image ):
        json={"name":"test.mp4","data":Video}
        try:
            response = requests.post("https://umairmirza-face-attendance.hf.space/run/predict", json={
                "data": [
                    TeacherID,
                    json,
                    Image,
                ]
            }).json()

            data = response["data"]
            return data[0]
        except Exception as e:
            return e


