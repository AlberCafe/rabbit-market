import axios from "axios";
import React, { useEffect, useState } from "react";
import { Card, Image } from "react-bootstrap";

const Welcome = props => {
  const [quotes, setQuotes] = useState("");

  useEffect(() => {
    if (quotes === "") {
      axios.get("https://type.fit/api/quotes").then(response => {
        setQuotes(response.data);
      });
    }
  }, [quotes]);

  return (
    <div>
      {/* <Card.Header>Quotes</Card.Header>
      <Card.Body style={{ overflowY: "scroll", height: "570px" }}>
        {quotes &&
          quotes.map((quote, id) => (
            <blockquote className="blockquote mb-0" key={id}>
              <p>{quote.text}</p>
              <footer className="blockquote-footer">{quote.author}</footer>
            </blockquote>
          ))}
      </Card.Body> */}
      <Image src="https://dnvefa72aowie.cloudfront.net/karrot-cs/etc/202007/5709077d31ef3372a0bd877ef2f1122453f310b2342733932e9f6223ecc9a021.png" fluid />
    </div>
  );
};

export default Welcome;
