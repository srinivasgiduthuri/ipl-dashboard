import { React } from "react";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { useQuery } from "@tanstack/react-query";
import { fetcherWithFetch } from "../lib/fetcher";

export const TeamPage = () => {
  const {
    data,
    error,
    isPending: loading,
  } = useQuery({
    queryKey: ["matches"],
    queryFn: () =>
      fetcherWithFetch(`http://localhost:8080/team/Delhi Capitals`),
  });

  return (
    <div className="TeamPage">
      {loading && <div>Loading...</div>}
      {error && <div>Error</div>}

      {data && (
        <div>
          <h1>{data.teamName}</h1>
          <MatchDetailCard match={data.matches[0]} />
          {data.matches.slice(1).map((match) => (
            <MatchSmallCard match={match} key={match.id} />
          ))}
        </div>
      )}
    </div>
  );
};
