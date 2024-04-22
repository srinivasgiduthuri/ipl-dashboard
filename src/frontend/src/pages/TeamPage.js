import { React } from "react";
import { useParams } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { useQuery } from "@tanstack/react-query";
import { fetcherWithFetch } from "../lib/fetcher";

export const TeamPage = () => {
  const { teamName } = useParams();
  const {
    data,
    error,
    isPending: loading,
  } = useQuery({
    queryKey: ["matches", teamName],
    queryFn: () => fetcherWithFetch(`http://localhost:8080/team/${teamName}`),
  });

  return (
    <div className="TeamPage">
      {loading && <div>Loading...</div>}
      {error && <div>Team not found.</div>}

      {data && data.matches && (
        <div>
          <h1>{data.teamName}</h1>
          <MatchDetailCard teamName={data.teamName} match={data.matches[0]} />
          {data.matches.slice(1).map((match) => (
            <MatchSmallCard
              teamName={data.teamName}
              match={match}
              key={match.id}
            />
          ))}
        </div>
      )}
    </div>
  );
};
